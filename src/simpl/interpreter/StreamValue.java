package simpl.interpreter;

import java.util.ArrayDeque;
import java.util.Deque;

public class StreamValue extends Value{
    public final Value seed;
    public final FunValue gen;
    public final Value list;
    public int limit = -1;
    private StreamPipeline pipeline = null;

    public StreamValue(Value seed, FunValue gen) {
        // Create a stream value through seed and generator
        this.seed = seed;
        this.gen = gen;
        this.list = null;
    }

    public StreamValue(Value list){
        // Create a stream value through list
        this.list = list;
        this.seed = null;
        this.gen = null;
    }

    public void mountFunction(FunValue f){
        // Mount a new function to the pipeline.
        // (logically this should be at the end of pipeline, but we insert it at the front.)
        // Reason: we wish to support the ability of "branching" streams.
        pipeline = new StreamPipeline(pipeline, f);
    }

    public void truncate(int limit) throws RuntimeError{
        if(limit < 0)
            throw new RuntimeError("Truncating the stream by a negative number");
        if(this.limit == -1)
            this.limit = limit;
        else
            this.limit = Math.min(this.limit, limit);
    }

    public Deque<FunValue> getPipeline(){
        Deque<FunValue> stack = new ArrayDeque<>();
        // Reverse the pipeline by a stack since we stored it in the opposite order.
        for(; pipeline!=null; pipeline=pipeline.next)
            stack.addFirst(pipeline.f);
        return stack;
    }

    @Override
    public StreamValue clone(){
        StreamValue ret;
        if(list == null)
            ret = new StreamValue(seed, gen);
        else
            ret = new StreamValue(list);
        ret.pipeline = pipeline;
        ret.limit = limit;
        return  ret;
    }

    @Override
    public String toString() {
        if(limit == -1)
            return "stream[∞]";
        return "stream[" + limit + "]";
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

    @Override
    public void markMemory(Mem M){
        if(seed != null)
            seed.markMemory(M);
        if(gen != null)
            gen.markMemory(M);
        if(list != null)
            list.markMemory(M);
    }
}
