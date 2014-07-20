package org.quickstep;

import javax.swing.*;

public interface ComponentBuilder
{
   GridBagSpec getSpec();

   JComponent build();

   class ComponentBuilderAdapter implements ComponentBuilder
   {
      private JComponent component;

      public ComponentBuilderAdapter(JComponent component)
      {
         this.component = component;
      }

      @Override
      public GridBagSpec getSpec()
      {
         return null;
      }

      @Override
      public JComponent build()
      {
         return component;
      }
   }
}
