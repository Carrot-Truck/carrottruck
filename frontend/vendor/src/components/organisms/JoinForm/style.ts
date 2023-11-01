import styled from 'styled-components';

export const JoinFormContainer = styled.div`
	position: relative;
	height: calc(100vh - 76px);
	padding-top: 3rem;

	.join-form {
		display: flex;
		flex-direction: column;
		gap: 2rem;

		padding: 3rem;
		border: 1px solid var(--gray-300);
		border-radius: var(--radius-s);

		.field {
			display: flex;
			flex-direction: column;
			gap: 10px;
		}

		.input {
			display: flex;
			flex-direction: column;
			gap: 5px;
		}

		.field-row {
			display: flex;
			flex-direction: row;

			.input {
				flex-grow: 1;
			}
		}
	}
	.next-btn {
		button {
			position: absolute;
			bottom: 2rem;
		}
	}
`;
