package _temp._spring._AOP._aspect.test;

public aspect _Aspect
{
    // 定义一个 PointCut，其名为 logPointcut，
    // 该 Pointcut代表了后面给出的切入点表达式，这样可复用该切入点表达式
    // 第一个星号表示返回值不限；
    // 第二个星号表示类名不限；
    // 第三个星号表示方法名不限；
    // 圆括号中..代表任意个数、类型不限的形参
    pointcut logPointcut()
            :execution(* _temp._spring._AOP._aspect.test.TargetClass.addUser(..));

    after():logPointcut()
            {
                System.out.println("add user after ... ... ...");
                System.out.println();
            }

    before(): logPointcut()
            {
                System.out.println("add user before ... ... ...");
            }


    //指定执行方法时执行下面代码块
    Object around():call(* _temp._spring._AOP._aspect.test.TargetClass.function(..))
            {
                System.out.println("begin transaction...");

                // 回调原来的目标方法
                Object rvt = proceed();

                System.out.println("end transaction...");
                System.out.println();

                return rvt;
            }
}
