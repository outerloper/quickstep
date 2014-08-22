package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SeparatorCommand extends CellCommand<SeparatorCommand>
{
   @Override
   public JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory)
   {
      return parentFactory.createSeparator(parentOrientation.getOther());
   }

   @Override
   protected CellSpec getSpec(Orientation parentOrientation)
   {
      CellSpec result = spec();
      if (parentOrientation.isHorizontal())
      {
         result.withAnchor(AX.CENTER, AY.BOTH);
      }
      else
      {
         result.withAnchor(AX.BOTH, AY.CENTER);
      }
      return result.overrideWith(super.getSpec(parentOrientation));
   }
}
