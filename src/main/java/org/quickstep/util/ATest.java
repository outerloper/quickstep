package org.quickstep.util;

public class ATest
{
   private interface AX {}
   private interface AY {}

   public static AnchorX LEFT = AnchorX.LEFT;
   public static AnchorX RIGHT = AnchorX.RIGHT;
   public static AnchorY TOP = AnchorY.TOP;
   public static AnchorY BOTTOM = AnchorY.BOTTOM;
   public static Fill NONE = Fill.NONE;
   public static Fill BOTH = Fill.BOTH;

   public static enum AnchorX implements AX
   {
      LEFT, RIGHT
   }

   public static enum AnchorY implements AY
   {
      TOP, BOTTOM
   }

   public static enum Fill implements AX, AY
   {
      NONE, BOTH
   }

   public void ax(AX a)
   {
      System.out.println(a);
   }

   public void ay(AY a)
   {
      System.out.println(a);
   }

   public void a(Fill a)
   {
      System.out.println(a);
   }

   public static void main(String[] args)
   {
      ATest test = new ATest();
      test.a(NONE);
      test.a(BOTH);
      test.ax(NONE);
      test.ax(BOTH);
      test.ax(LEFT);
      test.ax(RIGHT);
      test.ay(NONE);
      test.ay(BOTH);
      test.ay(TOP);
      test.ay(BOTTOM);
   }
}
