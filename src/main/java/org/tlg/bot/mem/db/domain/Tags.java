/**
 * 
 */
package org.tlg.bot.mem.db.domain;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Tags for photo, splitted by {@code Tags.DELIM} and trimmed.
 * @author "Maksim Vakhnik"
 *
 */
public class Tags {

    //tags splitted by this
    private static final String DELIM = " ";
    private final Set<String> tags;
    public Tags(final String tags) {
        this.tags = Tags.tags(tags);
    }
    
    public Set<String> getTags() {
        return new LinkedHashSet<>(tags);
    }
    
    private static Set<String> tags(final String tags) {
        final Set<String> asList = new LinkedHashSet<>();
        Arrays.asList(tags.split(DELIM)).forEach(tag -> {
            asList.add(tag.trim().toLowerCase());
        });
        return asList;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Tags [tags=");
        builder.append(tags);
        builder.append("]");
        return builder.toString();
    }

    public boolean isEmpty() {
        return this.tags.isEmpty();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Tags))
            return false;
        final Tags other = (Tags) obj;
        if (tags == null) {
            if (other.tags != null)
                return false;
        } else if (!tags.containsAll(other.tags))
            return false;
        return true;
    }
    
    
}
