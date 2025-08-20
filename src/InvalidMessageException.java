//when users send gibberish to Chunky

public class InvalidMessageException extends ChunkyException{
    public InvalidMessageException(String msg) {
        super(msg);
    }
}
