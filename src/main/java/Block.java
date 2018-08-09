import java.util.Base64;
import java.util.Date;
import java.util.Objects;

/**
 * Block in which the data is stored.
 */
public class Block
{

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
     * The default constructor for creating/mining a basic block.
     *
     * @param previousHash The previous block's hash.
     * @param data         The block's data.
     */
    public Block(
            String previousHash,
            char[] data)
    {
        this.previousHash = previousHash;
        this.data = data;
        this.timeStamp = new Date().getTime();

        // Calculating the hash should be the last part of the block's creation.
        this.hash = this.generateValidBlockHash();
    }

    /**
     * Constructor for creating a new block object based on existing data,
     * things like the hash and nonce are already calculated.
     *
     * @param hash         The block's hash.
     * @param previousHash The previous block's hash.
     * @param data         The block's data.
     * @param timeStamp    The block's creation date/timestamp.
     * @param nonce        The block's nonce, used for mining different hash with the same block-data.
     */
    public Block(String hash,
                 String previousHash,
                 char[] data,
                 long timeStamp,
                 long nonce)
    {
        this.hash = hash;
        this.previousHash = previousHash;
        this.data = data;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
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
        return HashUtils.applySHA256d(
                this.previousHash +
                        Long.toString(this.timeStamp) +
                        Long.toString(this.nonce) +
                        String.valueOf(this.data)
        );
    }

    /**
     * Generate a block-hash starting with at least six zero.
     *
     * @return A hash that's valid for the new block.
     */
    public String generateValidBlockHash()
    {
        String minedHash = this.calculateHash();
        while (!minedHash.substring(0, 2).equals("00"))
        {
            this.nonce++;
            minedHash = this.calculateHash();
        }
        System.out.println(String.format("New block is created with hash: %s.", minedHash));
        return minedHash;
    }

    /**
     * Encodes all block-data into a {@link Base64} {@link String}.
     *
     * @return A plain-old {@link String} object containing the base64 encodes raw block-data.
     */
    public String toBase64()
    {
        String rawData = String.format("%s,%s,%s,%s,%s", this.hash, this.previousHash, this.timeStamp, this.nonce, String.valueOf(this.data));
        return new String(Base64.getEncoder().encode(rawData.getBytes()));
    }

    /**
     * Decodes base64 into a {@link Block} object.
     *
     * @return An unverified {@link Block}.
     */
    static public Block fromBase64(String encodedBlockInput)
    {
        String rawBlock = new String(Base64.getDecoder().decode(encodedBlockInput.getBytes()));
        String[] blockData = rawBlock.split(",", 5);
        return new Block(blockData[0], blockData[1], blockData[4].toCharArray(), Long.valueOf(blockData[2]), Long.valueOf(blockData[3]));
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
                "\n\thash='" + hash + '\'' +
                "\n\tpreviousHash='" + previousHash + '\'' +
                "\n\tdata='" + String.valueOf(data) + '\'' +
                "\n\ttimeStamp=" + timeStamp +
                "\n\tnonce=" + nonce +
                "\n}";
    }
}
