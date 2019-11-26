package questionTwo;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MyArrayList<T> implements MyList<T> {
    private final List<T> list;

    public MyArrayList() {
        /**
         * Thread safe and concurrency safe implementation
         */
        this.list = Collections.synchronizedList(new CopyOnWriteArrayList<>());
    }

    public MyArrayList(List<T> other) {
        this.list = Collections.synchronizedList(new CopyOnWriteArrayList<>(other));
    }

    @Override
    public void add(T x) {
        this.list.add(x);
    }

    @Override
    public void add(List<T> lst) {
        lst.stream().parallel().forEach(list::add);
    }

    @Override
    public boolean forAll(Predicate<T> pr) {
        return list.stream().parallel().allMatch(pr);
    }

    @Override
    public boolean exists(Predicate<T> pr) {
        return list.stream().parallel().anyMatch(pr);
    }

    @Override
    public long count(Predicate<T> pr) {
        return list.size();
    }

    @Override
    public <R> MyList<R> map(Function<? super T, ? extends R> fn) {
        return new MyArrayList<>(list.stream().parallel().map(fn).collect(Collectors.toList()));
    }

    @Override
    public MyList<T> filter(Predicate<T> pr) {
        return new MyArrayList<>(list.stream().parallel().filter(pr).collect(Collectors.toList()));
    }

    @Override
    public <R> MyList<R> mapFilter(Function<? super T, ? extends R> fn, Predicate<T> pr) {
        return new MyArrayList<>(list.stream().parallel()
                .filter(pr)
                .map(fn)
                .collect(Collectors.toList()));
    }
}
