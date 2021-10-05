package com.mitsurin.tools.creating_best_party.model.party;

import java.util.List;
import java.util.ArrayList;

import com.mitsurin.tools.creating_best_party.model.compatibility.Compatibility;
import com.mitsurin.tools.creating_best_party.model.compatibility.CompatibilityXMLReader;
import com.mitsurin.tools.creating_best_party.model.compatibility.Type;

public class CreatingParty {
  private static Compatibility[] compatibilities = null;

  public CreatingParty(){
    if(CreatingParty.compatibilities == null){
      CompatibilityXMLReader reader = new CompatibilityXMLReader("/xml/compatibilities.xml");
      CreatingParty.compatibilities  = reader.getCompatibilities();
    }
  }

  public List<Party> calculateOptionParties(Type type1, Type type2){
    Compatibility compatibility1 = Compatibility.getCompatibility(CreatingParty.compatibilities, type1, type2);

    List<Party> parties = new ArrayList<>();

    for(int i = 0; i < CreatingParty.compatibilities.length; i++){
      Compatibility compatibility2 = CreatingParty.compatibilities[i];
      if(compatibility1.isWeakpointDuplicated(compatibility2)) continue;

      for(int j = i + 1; j < CreatingParty.compatibilities.length; j++){
        Compatibility compatibility3 = CreatingParty.compatibilities[j];

        if(compatibility1.isWeakpointDuplicated(compatibility3)) continue;
        if(compatibility2.isWeakpointDuplicated(compatibility3)) continue;

        Party party = new Party(compatibility1, compatibility2, compatibility3);
        parties.add(party);
      }
    }
    return parties;
  }
}
