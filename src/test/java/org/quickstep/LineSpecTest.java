package org.quickstep;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.quickstep.GridBagToolKit.*;
import static org.quickstep.GridBagToolKit.specWithFill;
import static org.quickstep.GridBagToolKit.specWithFillY;

public class LineSpecTest
{
   LineSpec lineSpec = lineSpec();

   @Test
   public void equals()
   {
      lineSpec = lineSpec().
         withDefault(spec().withIPadX(5)).
         withCell(1, specWithFillX());
      LineSpec expected = lineSpec().
         withDefault(spec().withIPadX(5)).
         withCell(1, specWithFillX());
      assertEquals(expected, lineSpec);
   }

   @Test
   public void specsAreNotEqualWhenDefaultOrCellsAreSpecifiedDifferentlyEvenWhenForEachCellSpecReturnsTheSameSpec()
   {
      lineSpec = lineSpec().
         withDefault(spec().withIPadX(5)).
         withCell(1, specWithFillX());
      LineSpec expected = lineSpec().
         withDefault(spec().withIPadX(5)).
         withCell(1, specWithFillX()).
         withCell(2, spec().withIPadX(5));
      assertFalse(expected.equals(lineSpec));
   }

   @Test
   public void whenOverridingLineSpecThenDefaultsAndCellsAreOverridden()
   {
      lineSpec = lineSpec().
         withDefault(spec().withIPadX(5)).
         withCell(1, specWithFillX());
      LineSpec overriding = lineSpec().
         withDefault(spec().withIPadY(5)).
         withCell(1, specWithFillY()).
         withCell(2, specWithFillY());
      lineSpec.overrideWith(overriding);
      LineSpec expected = lineSpec().
         withDefault(spec().withIPad(5)).
         withCell(1, specWithFill()).
         withCell(2, specWithFillY());
      assertEquals(expected, lineSpec.overrideWith(overriding));
   }

   @Test
   public void whenOverridingLineSpecWithNullThenEqualSpecIsReturned()
   {
      lineSpec = lineSpec().
         withDefault(spec().withIPadX(5)).
         withCell(1, specWithFillX());
      LineSpec expected = lineSpec().
         withDefault(spec().withIPadX(5)).
         withCell(1, specWithFillX());
      assertEquals(expected, lineSpec.overrideWith(null));
   }
}
