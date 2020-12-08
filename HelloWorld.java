import java.util.Random;
public class HelloWorld{
    public static void main(String[] args){
        char[] hello = {'H','E','L','L','O','!'};
        char[] world = hello;
        System.out.println(hello);
        world[0] = hello[0];
        world[1] = hello[1];
        world[2] = hello[2];
        world[3] = hello[3];
        world[4] = hello[4];
        world[5] = hello[5];
        world[0] = 'W';
        world[1] = 'O';
        world[2] = 'R';
        world[4] = 'D';
        String hello1 = new String(hello);
        String world1 = new String(world);
        String helloworld = hello1 + world1;
        System.out.println(helloworld);

        char ch1 = '!';
        char ch2 = ch1;
        System.out.println(ch1);
        System.out.println(ch2);

        Random r = new Random();
        long[] array = {10,100,1000,10000,100000};
        System.out.println(array[0]);
        // array[0] = r.nextInt();
        array[0] = array[1]+array[2]-10;
        System.out.println(array[0]);

    }
}