package exceptions;

public class TransactionsFolderProcessorException extends RuntimeException {
    public TransactionsFolderProcessorException (String message) {
        super(message);
    }
}
