import java.util.Random;

public class JavaBlockchain
{

    public static void main(String[] args)
    {
        Blockchain blockchain = new Blockchain();

        // Creating the first block without a previous block-hash.
        Block genesisBlock = new Block("0", "Hello world!".toCharArray());
        System.out.println(String.format("raw block-data: %s", genesisBlock.toDataBlock()));
        blockchain.addBlock(genesisBlock);

        String prevBlockHash = genesisBlock.getHash();

        Random random = new Random();

        // Generate 10 blocks with random data.
        while (blockchain.size() < 10)
        {
            System.out.println("======");
            Block block = new Block(prevBlockHash, Utils.randomString(random.nextInt(500) + 100));
            System.out.println(String.format("raw block-data: %s", block.toDataBlock()));
            blockchain.addBlock(block);

            prevBlockHash = block.getHash();
        }

        System.out.println("== Printing out blockchain...");
        System.out.println(blockchain);

        System.out.println("== Verifying blockchain...");
        blockchain.verify();

    }
}
