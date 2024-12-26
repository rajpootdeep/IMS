package com.deep.ims.utility;

import java.util.HashSet;

public class MappingUtility {

   public static final HashSet<String> priority =new HashSet<>();
    public static  final HashSet<String> status=new HashSet<>();
    public static final HashSet<String> type=new HashSet<>();
   static {
       priority.add("High");
       priority.add("Low");
       priority.add("Medium");
       status.add("Open");
       status.add("Closed");
       status.add("In progress");
       type.add("Enterprise");
       type.add("Individual");
       type.add("Government");
   }
}
