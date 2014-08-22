package org.quickstep;

import javax.swing.*;

public class LabelCommand extends CellCommand<LabelCommand>
{
   private String text;

   protected LabelCommand(String text)
   {
      this.text = text;
   }

   @Override
   public JComponent getComponent(GridBagToolKit.Orientation parentOrientation, ComponentFactory parentFactory)
   {
      return parentFactory.createLabel(text);
   }
}
