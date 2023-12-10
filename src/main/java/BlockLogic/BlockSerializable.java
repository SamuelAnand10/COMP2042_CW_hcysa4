package BlockLogic;

import java.io.Serializable;
/**
 * The `BlockSerializable` class represents a serializable version of the `Block` class.
 * It stores information about the row, column, and type of block.
 */
public class BlockSerializable implements Serializable {
    /**
     * The row of the block.
     */
    public final int row;
    /**
     * The column of the block.
     */
    public final int j;
    /**
     * The type of the block.
     */
    public final int type;
    /**
     * Constructs a `BlockSerializable` object with the specified row, column, and type.
     *
     * @param row  The row of the block.
     * @param j    The column of the block.
     * @param type The type of the block.
     */
    public BlockSerializable(int row , int j , int type) {
        this.row = row;
        this.j = j;
        this.type = type;
    }
}
