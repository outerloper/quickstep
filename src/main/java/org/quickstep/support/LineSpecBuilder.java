package org.quickstep.support;

import org.quickstep.CellSpec;
import org.quickstep.LineSpec;

import static org.quickstep.GridBagToolKit.*;

public class LineSpecBuilder<T>
{
   private final T owner;
   private final LineSpec lineSpec = lineSpec();

   public LineSpecBuilder(T owner)
   {
      this.owner = owner;
   }

   public T specifyDefault(CellSpec spec)
   {
      lineSpec.specifyDefault(spec);
      return owner;
   }

   public T specifyCell(int i, CellSpec spec)
   {
      lineSpec.specifyCell(i, spec);
      return owner;
   }

   public T specifyLine(LineSpec spec)
   {
      lineSpec.overrideWith(spec);
      return owner;
   }

   public LineSpec getLineSpec()
   {
      return lineSpec;
   }
}
