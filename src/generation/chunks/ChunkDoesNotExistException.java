package generation.chunks;

public class ChunkDoesNotExistException extends Exception {
    public ChunkDoesNotExistException(String message) {
        super(message);
    }
}
