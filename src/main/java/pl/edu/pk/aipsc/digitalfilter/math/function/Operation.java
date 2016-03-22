package pl.edu.pk.aipsc.digitalfilter.math.function;

public interface Operation extends Function {

    AbstractOperation add(Function f);

    AbstractOperation mul(Function f);

    AbstractOperation div(Function f);

    AbstractOperation sub(Function f);

    AbstractOperation onParam(Function f);

}
