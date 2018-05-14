import java.util.ArrayList;

/**
 * Blockchain which stores an array of {@link Block} objects.
 */
public class Blockchain extends ArrayList<Block>
{

    /**
     * Add a new {@link Block} into the {@link Blockchain} after checking it is valid.
     *
     * @param newBlock The to-be-added {@link Block}.
     */
    public void addBlock(Block newBlock)
    {

        if (!newBlock.calculateHash().equals(newBlock.getHash()))
        {
            System.out.println("Block has invalid block-hash!");
            return;
        }

        // Only check if block isn't the genesis block.
        if (this.size() != 0)
        {
            if (newBlock.getPreviousHash() != this.get(this.size() - 1).getHash())
            {
                System.out.println("New block doesn't tail previous block!");
                return;
            }
        }

        this.add(newBlock);
    }

    /**
     * Temporary method for verifying the whole chain. ToDo: Keep memory (ab)use in mind.
     */
    public boolean verify()
    {

        Block previousBlock = null;

        // Iterate through all blocks in the given blockchain.
        for (Block currentBlock : this)
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
