import java.util.Random;

public class JavaBlockchain
{

    public static void main(String[] args)
    {
        Random random = new Random();

        Blockchain blockchain = new Blockchain();

        // Creating the first block without a previous block-hash.
        Block genesisBlock = new Block("0", "Hello world!".toCharArray());
        System.out.println(String.format("raw block-data: %s", genesisBlock.toDataBlock()));
        blockchain.addBlock(genesisBlock);

        String prevBlockHash = genesisBlock.getHash();

        // Generate 10 blocks with random data.
        while (blockchain.size() < 10)
        {
            System.out.println("======");
            Block block = new Block(prevBlockHash, randomString(random));
            System.out.println(String.format("raw block-data: %s", block.toDataBlock()));
            blockchain.addBlock(block);

            prevBlockHash = block.getHash();
        }

        System.out.println("== Printing out blockchain...");
        System.out.println(blockchain);

        System.out.println("== Verifying blockchain...");
        blockchain.verify();

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
