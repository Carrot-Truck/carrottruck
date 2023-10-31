import React, { Dispatch, SetStateAction, ChangeEvent, ReactNode } from 'react';

import { InputWrapper } from './style';

interface IInputProps<T> {
	type: string;
	placeholder: string;
	value: T;
	setValue: Dispatch<SetStateAction<T>>;
	Icon?: ReactNode;
	disabled?: boolean;
}

function Input<T>(props: IInputProps<T>) {
	const { type, placeholder, value, setValue, Icon, disabled } = props;

	const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
		setValue(e.target.value as SetStateAction<T>);
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
			/>
		</InputWrapper>
	);
}

export default Input;
