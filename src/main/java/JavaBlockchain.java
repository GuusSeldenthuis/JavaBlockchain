import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JavaBlockchain
{

    public static List<Block> blockchain = new ArrayList<>();

    public static void main(String[] args)
    {
        // Creating the first block without a previous block-hash.
        Block genesisBlock = new Block("0", "Hello world!".toCharArray());
        blockchain.add(genesisBlock);

        String prevBlockHash = genesisBlock.getHash();

        Random random = new Random();

        // Generate 10 blocks with random data.
        while (blockchain.size() < 10)
        {
            System.out.println("======");
            Block block = new Block(prevBlockHash, Utils.randomString(random.nextInt(500) + 100));
            System.out.println(String.format("raw block-data: %s", block.toDataBlock()));
            blockchain.add(block);

            prevBlockHash = block.getHash();
        }

        System.out.println("== Printing out blockchain...");
        System.out.println(blockchain);

        System.out.println("== Verifying blockchain...");
        verifyBlocks(blockchain);

    }

    /**
     * Temporary method for verifying the whole chain. ToDo: Keep memory (ab)use in mind.
     */
    public static Boolean verifyBlocks(List<Block> blocks)
    {

        Block previousBlock = null;

        // Iterate through all blocks in the given blockchain.
        for (Block currentBlock : blocks)
        {

            // Check if current block's hash is valid.
            if (!currentBlock.getHash().equals(currentBlock.calculateHash()))
            {
                System.out.println(String.format("Block: %s has a invalid hash.", currentBlock.getHash()));
                return false;
            }

            // Check previous block's hash if the current block isn't the genesis/first block.
            if (previousBlock != null)
            {
                //compare previous hash and registered previous hash
                if (!previousBlock.getHash().equals(currentBlock.getPreviousHash()))
                {
                    System.out.println(String.format("Block: %s has a invalid previous block-hash!"));
                    return false;
                }
            }
            System.out.println(String.format("Block: %s is valid.", currentBlock.getHash()));
            previousBlock = currentBlock;
        }
        return true;
    }
}
