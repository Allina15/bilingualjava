package bilingual.exception;

public class InvalidQuestionException extends RuntimeException{
    public InvalidQuestionException(String message) {
        super(message);
    }
}
