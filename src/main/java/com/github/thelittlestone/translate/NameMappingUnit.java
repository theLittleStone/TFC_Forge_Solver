package com.github.thelittlestone.translate;

import java.util.Objects;

/**
 * Created by theLittleStone on 2023/6/8.
 */
public class NameMappingUnit {
    public String origName;
    public String transName;

    @Override
    public String toString() {
        return transName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NameMappingUnit that = (NameMappingUnit) o;

        return Objects.equals(origName, that.origName);
    }

    @Override
    public int hashCode() {
        return origName != null ? origName.hashCode() : 0;
    }
}
