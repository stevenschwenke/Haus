
/**
 * Created by steven on 18.09.14.
 */
public class QuestionWithAnswer {

    private String _id;
    private String question;
    private String answer;


    private String category;

    /**
     * Private constructor only needed for object mapping.
     */
    private QuestionWithAnswer() {

    }

    public QuestionWithAnswer(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "QuestionWithAnswer{" +
                "_id='" + _id + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
