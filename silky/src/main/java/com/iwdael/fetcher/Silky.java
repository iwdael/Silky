package com.iwdael.fetcher;

/**
 * @author : iwdael
 * @mail : iwdael@outlook.com
 * @project : https://github.com/iwdael/Fetcher
 */
public class Silky {

    public static FetchBuilder newFetcher() {
        return new FetchBuilder();
    }

    public static class FetchBuilder {
        public <S, T> FetchChain<S, T> fetch(Class<S> source, Fetcher.Factory<S, T> factory) {
            return new FetchChain<>(source, factory, null);
        }
    }

    public static class Fetchers {
        public final Class<?> source;
        public final Class<?> target;
        public final FetchChain<?, ?> chain;

        public Fetchers(Class<?> source, Class<?> target, FetchChain<?, ?> chain) {
            this.source = source;
            this.target = target;
            this.chain = chain;
        }
    }

    public static class FetchChain<S, T> {
        protected final Fetcher.Factory<S, T> factory;
        protected final Class<?> source;
        protected final FetchChain<?, ?> pre;
        protected FetchChain<?, ?> next = null;

        public FetchChain(Class<?> source, Fetcher.Factory<S, T> factory, FetchChain<?, ?> pre) {
            this.factory = factory;
            this.source = source;
            this.pre = pre;
        }

        public <D> FetchChain<T, D> fetch(Fetcher.Factory<T, D> factory) {
            FetchChain<T, D> fetcher = new FetchChain<T, D>(source, factory, this);
            this.next = fetcher;
            return fetcher;
        }

        public Fetchers build(Class<T> target) {
            FetchChain<?, ?> p = this;
            while (p.pre != null) p = p.pre;
            return new Fetchers(p.source, target, p);
        }
    }


    public static InjectBuilder newInjector() {
        return new InjectBuilder();
    }

    public static class InjectBuilder {
        public <S, T> TransformChain<S, T> transform(Class<S> source, Transformer.Factory<S, T> factory) {
            return new TransformChain<>(source, factory, null);
        }
    }

    public static class Injectors {
        public final Class<?> source;
        public final Class<?> target;
        public final TransformChain<?, ?> chain;

        public Injectors(Class<?> source, Class<?> target, TransformChain<?, ?> chain) {
            this.source = source;
            this.target = target;
            this.chain = chain;
        }
    }

    public static class InjectChain<S, T> {
        protected final Injector.Factory<S, T> factory;
        protected final Class<?> source;
        protected final TransformChain<?, ?> pre;


        public InjectChain(Class<?> source, Injector.Factory<S, T> factory, TransformChain<?, ?> pre) {
            this.factory = factory;
            this.source = source;
            this.pre = pre;
        }

        public Injectors build(Class<T> target) {
            TransformChain<?, ?> p = this.pre;
            while (p.pre != null) p = p.pre;
            return new Injectors(p.source, target, p);
        }
    }

    public static class TransformChain<S, T> {
        protected final Transformer.Factory<S, T> factory;
        protected final Class<?> source;
        protected final TransformChain<?, ?> pre;
        protected TransformChain<?, ?> next = null;

        public TransformChain(Class<?> source, Transformer.Factory<S, T> factory, TransformChain<?, ?> pre) {
            this.factory = factory;
            this.source = source;
            this.pre = pre;
        }

        public <D> TransformChain<T, D> transform(Transformer.Factory<T, D> factory) {
            TransformChain<T, D> fetcher = new TransformChain<T, D>(source, factory, this);
            this.next = fetcher;
            return fetcher;
        }

        public <D> InjectChain<T, D> inject(Injector.Factory<T, D> factory) {
            return new InjectChain<>(source, factory, this);
        }
    }


}
