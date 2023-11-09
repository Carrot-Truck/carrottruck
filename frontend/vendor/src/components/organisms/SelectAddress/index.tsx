// import Button from "components/atoms/BigButton";
import { SelectAddressContainer } from "./style";
import { useEffect } from "react";
import { getDong, getSigungu, getSido } from "api/address";

interface ISelectAddressProps {
  sidoId: number | null;
  sigunguId: number | null;
}

interface AddressItem {
  id: number;
  name: string;
}

interface AddressResponse {
  data: AddressItem[];
}

function SelectAddress({ sidoId, sigunguId }: ISelectAddressProps) {
  useEffect(() => {
    console.log(1);
    const fetchData = async () => {
      if (sidoId == null) {
        getSido(
          ({ data }: AddressResponse) => {
            console.log("!!", data);
          },
          (error: any) => {
            console.log(error);
          }
        );
      } else if (sigunguId == null) {
        getSigungu(
          sidoId,
          (data: Promise<AddressResponse>) => {
            console.log(data);
          },
          (error: any) => {
            console.log(error);
          }
        );
      } else {
        getDong(
          sigunguId,
          (data: Promise<AddressResponse>) => {
            console.log(data);
          },
          (error: any) => {
            console.log(error);
          }
        );
      }
    };
    fetchData();
  }, []);

  return <SelectAddressContainer></SelectAddressContainer>;
}

export default SelectAddress;
