import org.junit.jupiter.api.*;

public class JunitCycleTest {
    @BeforeAll

    static void beforAll(){
        System.out.println("@BeforeAll");
    }
    @BeforeEach
    public void beforeEach(){
        System.out.println("@BeforeEach");
    }
    @Test
    public void test1(){
        System.out.println("@test1");
    }
    @Test
    public void test2(){
        System.out.println("@test2");
    }
    @Test
    public void test3(){
        System.out.println("@test3");
    }
    @AfterAll

    static void AfterAll(){
        System.out.println("@AfterAll");
    }
    @AfterEach
    public void AfterEach(){
        System.out.println("@AfterEach");
    }
}
