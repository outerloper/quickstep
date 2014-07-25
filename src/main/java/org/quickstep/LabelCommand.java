package org.quickstep;

public class LabelCommand implements GridBagCommand
{
   private GridBagSpec spec;
   private String text;

   protected LabelCommand(String text, GridBagSpec spec)
   {
      this.text = text;
      this.spec = spec.derive();
   }

   @Override
   public void apply(GridBagBuilder builder)
   {
      builder.moveToFreeCell();
      builder.placeComponent(builder.createDefaultLabel(text), spec);
   }
}