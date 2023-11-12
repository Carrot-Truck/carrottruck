import { TitleTextWrapper } from "./style";

interface ITitleTextProps {
  text: string;
  size: "s" | "m" | "l";
}

function TitleText({ text, size }: ITitleTextProps) {
  return <TitleTextWrapper $size={size}>{text}</TitleTextWrapper>;
}

export default TitleText;
