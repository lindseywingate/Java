interface Algorithm
{
    public void setInstance(Object instance);
    public void setConstraint(int index);
    public void setConstraint(int index, long value);
    public void run();
    public String getName();
    public long getStepCount();
    public long getSpace();
    public Object getResult();
}
