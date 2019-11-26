package questionTwo;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface MyList<E> {
    void add(E x);

    void add(List<E> lst);

    boolean forAll(Predicate<E> pr);

    boolean exists(Predicate<E> pr);

    long count(Predicate<E> pr);

    <R> MyList<R> map(Function<? super E, ? extends R> fn);

    MyList<E> filter(Predicate<E> pr);

    <R> MyList<R> mapFilter(Function<? super E, ? extends R> fn, Predicate<E> pr);
}