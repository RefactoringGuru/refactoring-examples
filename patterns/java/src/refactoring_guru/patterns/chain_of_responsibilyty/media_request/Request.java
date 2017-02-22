package refactoring_guru.patterns.chain_of_responsibilyty.media_request;

public abstract class Request {
    public Request nextInChain;

    public abstract void setNext(Request next);
    public abstract String process(String request);
}
