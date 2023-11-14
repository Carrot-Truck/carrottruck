import { ChangeEvent, ReactNode } from 'react';

import { InputWrapper } from './style';

interface IInputProps<T> {
  type: string;
  placeholder: string;
  value: T;
  Icon?: ReactNode;
  disabled?: boolean;
  onKeyPress?: (event: React.KeyboardEvent<HTMLInputElement>) => void;
  onChange?: (event: ChangeEvent<HTMLInputElement>) => void;
}

function Input<T>(props: IInputProps<T>) {
  const { type, placeholder, value, Icon, disabled, onKeyPress, onChange  } = props;
  

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (onChange) {
      onChange(e);
    }
  };

  return (
    <InputWrapper $isIcon={Icon !== undefined}>
      <div className="icon">{Icon}</div>
      <input
        type={type}
        placeholder={placeholder}
        value={value as string}
        onChange={handleChange}
        disabled={disabled}
        onKeyPress={onKeyPress}
      />
    </InputWrapper>
  );
}

export default Input;
