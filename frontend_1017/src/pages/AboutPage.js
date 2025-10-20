import useCustomLogin from "../hooks/useCustomLogin"
import BasicLayout from "../layout/BasicLayout";

const AboutPage = () => {
  const {isLogin, moveToLoginReturn} = useCustomLogin();
  if(!isLogin) return moveToLoginReturn();
  return (
    <BasicLayout>
      <div className="text-3xl">
        <div>AboutPage</div>
      </div>
    </BasicLayout>
  );
};

export default AboutPage;
