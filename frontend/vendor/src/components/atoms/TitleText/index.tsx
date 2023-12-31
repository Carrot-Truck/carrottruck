import { TitleTextWrapper } from "./style";

interface ITitleTextProps {
  text: string;
  size: "s" | "m" | "l";
  textAlign: "left" | "center" | "right";
}

function TitleText({ text, size, textAlign }: ITitleTextProps) {
  return (
    <TitleTextWrapper $size={size} $textAlign={textAlign}>
      {text}
    </TitleTextWrapper>
  );
}

export default TitleText;
