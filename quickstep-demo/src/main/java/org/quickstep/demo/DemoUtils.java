package org.quickstep.demo;

import javax.swing.*;

public class DemoUtils
{
   private DemoUtils()
   {
   }

   public static void setSystemLookAndFeel() {
      try
      {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }
}
