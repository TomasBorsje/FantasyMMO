package tomasborsje.plugin.fantasymmo.core.enums;

import com.mojang.authlib.properties.Property;

public enum FakePlayerSkin {
    MECHANIC(new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTY3ODMyNTk2MzA5NywKICAicHJvZmlsZUlkIiA6ICJkODcwNTk0ZjY2MmM0NzRiOGU2ZGJiOGU2ZmQyMzYwYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdXBlckZyYW5jeV8iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJiNGEyNjUxZjg1MmRmMDQ4MDM4ZjAyYjEwZjU0NDI3OWQ5NDkwMTk5MGM4ZDQ1M2MyZjlkZmY2NWRlMjE5OSIKICAgIH0KICB9Cn0=", "Ekpw8YgqocZaU8SoZ/q7FrMciN1q0raHPVM8s/B+YxzYV75eoW43UIo/ZI4Zbkc7z9lNiYlt1isZ7hcRGUpWfjGauhnL6pTZ8y+ZreGqFHlAw/UG0Bzhzh9su0uzGbXjJPiGeY2R8fmL7XQ2yD2ftK7mCbkcNxpLCDmHuwO4jKjiqjTlWQaIvm4qEZ9EA31VlG6zq1DLgVlv0fs2I64EoBrMydM76/9/cX9PWdVp+zP1OMY0WET/6c3BStLq5FbXE/oGoUhsPNx/Oi4JkiDq680HnpM/+3K3NL8yGt5txinRmj50uXeyEzPFEQjHhjoYrLwXdgodx4ZZmJ6BTE7tSSJfgv8zlssyW+ZLBdK9qsW6PPUAhsSLymbCXD+nQD/f08zRlQ8CaPFu7keEppjBHfQboWnnTq2dBriI3Ja3MFIsQbmFXmjnIcMH8017ifgNuSxnK8EPC6iLiOO7l8aB5iDNBZ2RBXefNZ4eHVS5LB5C+Nw8JYurroTdcG8JYFHXiszVhZO3mh59YZnofqkZYc6un08P/tvh+mTwbySvWrxyXNkqni7n6A7syJvMK5hh0aNHca3r0tZ+fx9VlDIrsjoezLzKgQHqDE9jJVYQfVj5c5mYH0E1L2yYsvc0O/vtvimrhXOot2O5LLsCCDnpvjJjw/yuPO/nWa46JmnRD3c="));

    public final Property texture;
    FakePlayerSkin(Property texture) {
        this.texture = texture;
    }
}
