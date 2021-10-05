package com.mitsurin.tools.creating_best_party.model.compatibility;

import java.util.Map;

public class Compatibility {
    public final Type[] types;
    public final Map<Type, Integer> weakpoints;

    public Compatibility(Type[] types, Map<Type, Integer> weakpoints) {
        this.types = types;
        this.weakpoints = weakpoints;
    }

    public static Compatibility getCompatibility(Compatibility[] compatibilities, Type type1, Type type2){
        for(int i = 0; i < compatibilities.length; i++){
            if(
                compatibilities[i].types[0] == type1 && compatibilities[i].types[1] == type2 || 
                compatibilities[i].types[0] == type2 && compatibilities[i].types[1] == type1
            ) return compatibilities[i];
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("types (");
        sb.append("t1 = " + (types[0] == null ? "null" : types[0].name) + ", t2 = " + (types[1] == null ? "null" : types[1].name) + ")\n");
        sb.append("  weakpoints:\n");
        weakpoints.forEach((k, v) -> {
            sb.append("    type = " + (k == null ? "null" : k.name) + ", level = " + v + "\n");
        });

        return sb.toString();
    }

    public boolean isWeakpointDuplicated(Compatibility compatibility){
        for(Map.Entry<Type, Integer> weakpoint : this.weakpoints.entrySet()){
            if(
                weakpoint.getValue() > 0 && 
                compatibility.weakpoints.containsKey(weakpoint.getKey()) && 
                compatibility.weakpoints.get(weakpoint.getKey()) > 0
            ) return true;
        }
        return false;
    }
}