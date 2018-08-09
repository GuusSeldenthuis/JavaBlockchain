import java.util.Random;

public class JavaBlockchain
{

    public static void main(String[] args)
    {
        Random random = new Random();

        Blockchain blockchain = new Blockchain();

        // Creating the first block without a previous block-hash.
        Block genesisBlock = new Block("0", "Hello, world!".toCharArray());
        System.out.println(String.format("raw block-data: %s", genesisBlock.toBase64()));
        blockchain.addBlock(genesisBlock);

        System.out.println(genesisBlock.toBase64());

        System.out.println("== Printing out blockchain...");
        System.out.println(blockchain);

        System.out.println("== Verifying blockchain...");
        blockchain.verify();

        String lastBlockBase64 = blockchain.get(blockchain.size() - 1).toBase64();
        System.out.println(String.format("== Last block in base64: %s", lastBlockBase64));
        System.out.println("== Constructed block based on data: ");
        System.out.println(Block.fromBase64(lastBlockBase64));

    }

    /**
     * Temporary method for generating a random string of chars. (Random in length and content.)
     */
    public static char[] randomString(Random random)
    {
        int randomLenght = random.nextInt(500) + 100;
        String AB = " .!@#$%^&*()_+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        char[] dataString = new char[randomLenght];
        for (int i = 0; i < randomLenght; i++)
        {
            dataString[i] = AB.charAt(random.nextInt(AB.length()));
        }
        return dataString;
    }
}
