
class IntGenerator implements InstanceGenerator
{
   public Object makeInstance(long seed, long [] params)
   {
   	return new Long(params[0]);
   }
}
