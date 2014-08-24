package org.quickstep.support;

import org.quickstep.CellSpec;
import org.quickstep.LineSpec;

import static org.quickstep.GridBagToolKit.*;

public class LineSpecSupport<T>
{
   private final T owner;
   private final LineSpec lineSpec = lineSpec();

   public LineSpecSupport(T owner)
   {
      this.owner = owner;
   }

   public T withDefault(CellSpec spec)
   {
      lineSpec.withDefault(spec);
      return owner;
   }

   public T withCell(int i, CellSpec spec)
   {
      lineSpec.withCell(i, spec);
      return owner;
   }

   public T withLine(LineSpec spec)
   {
      lineSpec.overrideWith(spec);
      return owner;
   }

   public LineSpec getLineSpec()
   {
      return lineSpec;
   }
}
