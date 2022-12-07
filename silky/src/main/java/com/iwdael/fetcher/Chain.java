package com.iwdael.fetcher;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/Fetcher
 */
public class Chain<F> {
    public final Class<?> source;
    public final Class<?> target;
    public final F chain;

    public Chain(Class<?> source, Class<?> target, F chain) {
        this.source = source;
        this.target = target;
        this.chain = chain;
    }

    public static FetchBuilder newFetchBuilder() {
        return new FetchBuilder();
    }

    public static class FetchBuilder {
        public <S, T> Fetch<S, T> fetch(Class<S> source, Fetcher.Factory<S, T> factory) {
            return new Fetch<>(source, factory, null);
        }
    }

    public static class Fetch<S, T> {
        protected final Fetcher.Factory<S, T> factory;
        protected final Class<?> source;
        protected final Fetch<?, ?> pre;
        protected Fetch<?, ?> next = null;

        public Fetch(Class<?> source, Fetcher.Factory<S, T> factory, Fetch<?, ?> pre) {
            this.factory = factory;
            this.source = source;
            this.pre = pre;
        }

        public <D> Fetch<T, D> fetch(Fetcher.Factory<T, D> factory) {
            Fetch<T, D> fetcher = new Fetch<T, D>(source, factory, this);
            this.next = fetcher;
            return fetcher;
        }

        public Chain<Fetch<?, ?>> build(Class<T> target) {
            Fetch<?, ?> p = this;
            while (p.pre != null) p = p.pre;
            return new Chain<>(p.source, target, p);
        }
    }


    public static InjectBuilder newInjectBuilder() {
        return new InjectBuilder();
    }

    public static class InjectBuilder {
        public <S, T> Transform<S, T> transform(Class<S> source, Transformer.Factory<S, T> factory) {
            return new Transform<>(source, factory, null);
        }
    }

    public static class Transform<S, T> {
        protected final Transformer.Factory<S, T> factory;
        protected final Class<?> source;
        protected final Transform<?, ?> pre;
        protected Object next = null;

        public Transform(Class<?> source, Transformer.Factory<S, T> factory, Transform<?, ?> pre) {
            this.factory = factory;
            this.source = source;
            this.pre = pre;
        }

        public <D> Transform<T, D> transform(Transformer.Factory<T, D> factory) {
            Transform<T, D> fetcher = new Transform<T, D>(source, factory, this);
            this.next = fetcher;
            return fetcher;
        }

        public <D> Inject<T, D> inject(Injector.Factory<T, D> factory) {
            Inject<T, D> inject = new Inject<>(source, factory, this);
            this.next = inject;
            return inject;
        }
    }


    public static class Inject<S, T> {
        protected final Injector.Factory<S, T> factory;
        protected final Class<?> source;
        protected final Transform<?, ?> pre;


        public Inject(Class<?> source, Injector.Factory<S, T> factory, Transform<?, ?> pre) {
            this.factory = factory;
            this.source = source;
            this.pre = pre;
        }

        public Chain<Transform<?, ?>> build(Class<T> target) {
            Transform<?, ?> p = this.pre;
            while (p.pre != null) p = p.pre;
            return new Chain<>(p.source, target, p);
        }
    }

}