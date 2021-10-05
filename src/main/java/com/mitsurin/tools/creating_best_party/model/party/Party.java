package com.mitsurin.tools.creating_best_party.model.party;

import java.util.List;
import java.util.Map;

import com.mitsurin.tools.creating_best_party.model.compatibility.Compatibility;
import com.mitsurin.tools.creating_best_party.model.compatibility.Type;

public class Party {
  private Compatibility compatibility1;
  private Compatibility compatibility2;
  private Compatibility compatibility3;

  public Party(Compatibility compatibility1, Compatibility compatibility2, Compatibility compatibility3){
    this.compatibility1 = compatibility1;
    this.compatibility2 = compatibility2;
    this.compatibility3 = compatibility3;
  }

  private Compatibility[] convertParty2Compatibilities(){
    return new Compatibility[]{compatibility1, compatibility2, compatibility3};
  }

  public static String convertPartyList2JSON(List<Party> partyList){
    StringBuilder json = new StringBuilder("[");
    
    for(int i = 0; i < partyList.size(); i++){
      json.append("{\"compatibilities\" : [");

      for(Compatibility compatibility : partyList.get(i).convertParty2Compatibilities()){
        json.append("{");

        json.append("\"types\" : [ \"" + compatibility.types[0].toString() + "\",\"" + compatibility.types[1].toString() + "\"], ");
        json.append("\"weakpoints\" : [");
        for(Map.Entry<Type, Integer> weakpoint : compatibility.weakpoints.entrySet()){
          json.append("{\"" + weakpoint.getKey().toString() + "\" : " + weakpoint.getValue().toString() + "},");
        }
        json.setLength(json.length() - 1);
        json.append("]");

        json.append("},");
      }
      json.setLength(json.length() - 1);

      json.append("]}");
      if(i < partyList.size() - 1){
        json.append(",");
      }
    }

    json.append("]");

    return json.toString();
  }
}
