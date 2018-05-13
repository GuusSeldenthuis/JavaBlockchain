import java.util.Base64;
import java.util.Date;
import java.util.Objects;

/**
 * Block object.
 */
public class Block
{
    // The block's mining-difficulty.
    private int difficulty;

    // The block's hash.
    private String hash;

    // The previous block's hash.
    private String previousHash;

    // The block's data.
    private char[] data;

    // The block's creation date/timestamp.
    private long timeStamp;

    // The block's nonce, used for mining different hash with the same block-data.
    private long nonce;

    /**
     * The default constructor for creating a basic block.
     *
     * @param difficulty   The block's mining-difficulty.
     * @param previousHash The previous block's hash.
     * @param data         The block's data.
     */
    public Block(
            int difficulty,
            String previousHash,
            char[] data)
    {
        this.difficulty = difficulty;
        this.previousHash = previousHash;
        this.data = data;
        this.timeStamp = new Date().getTime();

        // Calculating the hash should be the last part of the block's creation.
        this.hash = this.mineValidBlockHash();
    }

    /**
     * Get the block's hash.
     *
     * @return See {@link #hash}.
     */
    public String getHash()
    {
        return this.hash;
    }

    /**
     * Get the block's previous block's hash.
     *
     * @return See {@link #previousHash}.
     */
    public String getPreviousHash()
    {
        return this.previousHash;
    }

    /**
     * Get the block-data.
     *
     * @return See {@link #data}.
     */
    public char[] getData()
    {
        return this.data;
    }

    /**
     * Get the block's creation date/timestamp as long.
     *
     * @return See {@link #timeStamp}.
     */
    public long getTimeStamp()
    {
        return this.timeStamp;
    }

    /**
     * Returns the block as hash value.
     *
     * @return A {@link String} object representing the block's hash.
     */
    public String calculateHash()
    {
        return Utils.applySHA256d(
                this.previousHash +
                        Long.toString(this.timeStamp) +
                        Long.toString(nonce) +
                        String.valueOf(this.data)
        );
    }

    /**
     * Magic mining method for generating the block's valid hash.
     *
     * @return A hash that's valid for the block.
     */
    public String mineValidBlockHash()
    {
        // Generate the hash's first required chars. (Diff 5 is for example 00000).
        String targetHash = new String(new char[this.difficulty]).replace('\0', '0');
        System.out.println(String.format("Mining with difficulty: %s and targetHash: %s.", this.difficulty, targetHash));

        String minedHash = this.calculateHash();
        while (!minedHash.substring(0, difficulty).equals(targetHash))
        {
            this.nonce++;
            // Check if the timestamp is still valid over 100,000 nonce-tries.
            if (this.nonce % 100000 == 0)
            {
                if (this.timeStamp != new Date().getTime())
                {
                    this.timeStamp = new Date().getTime();
                    this.nonce = 0;
                }
            }
            minedHash = this.calculateHash();
        }
        System.out.println(String.format("Block is mined with hash %s!", minedHash));
        return minedHash;
    }

    /**
     * Encodes all block-data into a {@link Base64} {@link String}.
     *
     * @return A plain-old {@link String} object containing the base64 encodes raw block-data.
     */
    public String toDataBlock()
    {
        String rawData = String.format("%s,%s,%s,%s", this.previousHash, this.timeStamp, this.nonce, String.valueOf(this.data));
        return new String(Base64.getEncoder().encode(rawData.getBytes()));
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return Objects.equals(this.hash, block.hash);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.hash);
    }

    @Override
    public String toString()
    {
        return "Block{" +
                "\n\tdifficulty=" + difficulty +
                "\n\thash='" + hash + '\'' +
                "\n\tpreviousHash='" + previousHash + '\'' +
                "\n\tdata='" + String.valueOf(data) + '\'' +
                "\n\ttimeStamp=" + timeStamp +
                "\n\tnonce=" + nonce +
                "\n}";
    }
}
