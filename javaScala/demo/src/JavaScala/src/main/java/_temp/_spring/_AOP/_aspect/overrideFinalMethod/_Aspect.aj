package _temp._spring._AOP._aspect.overrideFinalMethod;

import org.aspectj.lang.annotation.Aspect;

@Aspect
public aspect _Aspect
{
    //指定执行方法时执行下面代码块
    Object around():call(* _temp._spring._AOP._aspect.overrideFinalMethod.entity.ChildInherit.print(..))
            {
                System.out.println("begin ...");

                //回调原来的目标方法
                Object rvt = proceed();

                System.out.println("end  ...");
                return rvt;
            }

    //指定执行方法时执行下面代码块
    Object around():call(* java.lang.String.*(..))
            {
                System.out.println("begin ...");

                //回调原来的目标方法
                Object rvt = proceed();

                System.out.println("end  ...");
                return rvt;
            }
}
