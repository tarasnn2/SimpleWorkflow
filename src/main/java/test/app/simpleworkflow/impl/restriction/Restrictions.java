package test.app.simpleworkflow.impl.restriction;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Restrictions extends HashSet<Restriction> {

    private Restrictions(int initialCapacity) {
        super(initialCapacity);
    }

    private Restrictions() {
    }

    private Restrictions(Collection<? extends Restriction> c) {
        super(c);
    }

    public static class RestrictionsBuilder {
        private Set<Restriction> set = new HashSet<>();

        public static RestrictionsBuilder getBuilder() {
            return new RestrictionsBuilder();
        }

        public RestrictionsBuilder add(Restriction restriction) {
            set.add(restriction);
            return this;
        }

        public Restrictions build() {
            Restrictions restrictions = new Restrictions(set.size());
            restrictions.addAll(set);
            return restrictions;
        }
    }
}