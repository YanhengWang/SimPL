package simpl.interpreter;

public class StreamPipeline {
    public final StreamPipeline next;
    public final FunValue f;

    public StreamPipeline(StreamPipeline next, FunValue f) {
        this.next = next;
        this.f = f;
    }
}
