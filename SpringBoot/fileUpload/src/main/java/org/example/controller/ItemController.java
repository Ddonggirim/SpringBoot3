package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.form.ItemForm;
import org.example.domain.Item;
import org.example.domain.UploadFile;
import org.example.fileservice.FileStore;
import org.example.repository.ItemRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemRepository itemRepository;
    private final FileStore fileStore;
    @GetMapping("/add")
    public String addItem(@ModelAttribute ItemForm itemForm){
        return "item/addForm";
    }
    @PostMapping("/add")
    public String add(@ModelAttribute ItemForm itemForm,
                      RedirectAttributes redirectAttributes) throws IOException {
        MultipartFile mAttachFile = itemForm.getAttachFile();
        UploadFile attachFile = fileStore.storeFile(mAttachFile);
        List<MultipartFile> mImageFiles = itemForm.getImageFiles();
        List<UploadFile> imageFiles = fileStore.storeFiles(mImageFiles);
        Item item = new Item(itemForm.getItemName(), attachFile, imageFiles);
        itemRepository.save(item);
        log.info("등록 itemForm : {}", itemForm);
        log.info("저장 item : {}", item);
        redirectAttributes.addAttribute("itemId", item.getId());
        return "redirect:{itemId}";
    }
    @GetMapping("/{id}")
    public String items(@PathVariable Long id, Model model){
        model.addAttribute("item", itemRepository.findById(id));
        return "item/itemView";
    }
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException{
        return new UrlResource("file:"+fileStore.getFullPath(filename));
    }
    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException{
        Item item = itemRepository.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();
        UrlResource resource = new UrlResource("file:"+fileStore.getFullPath(storeFileName));
        log.info("uploadFileName : {}", uploadFileName);
        String encodeUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName +"\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
