package org.quickstep;

import javax.swing.*;

import static org.quickstep.GridBagToolKit.*;

public class SeparatorCommand extends CellCommand<SeparatorCommand>
{
   @Override
   public JComponent getComponent(Orientation orientation, ComponentFactory factory)
   {
      return factory.createSeparator(orientation.getOther());
   }

   @Override
   protected CellSpec getSpec(Orientation orientation)
   {
      CellSpec result = spec();
      if (orientation.isHorizontal())
      {
         result.withAnchor(AX.CENTER, AY.BOTH);
      }
      else
      {
         result.withAnchor(AX.BOTH, AY.CENTER);
      }
      return result.overrideWith(super.getSpec(orientation));
   }
}
