import React, { useEffect, useRef, useState } from 'react';
import { Transition } from 'react-transition-group';
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert';
import { CircleAlert } from 'lucide-react';

type Props = {
  title: string;
  mess: string;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
};

export function AlertDemo(props: Props) {
  const [show, setShow] = useState(false);
  const alertRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    setShow(true);
    setTimeout(() => {
      setShow(false);
      setTimeout(() => {
        props.setOpen(false);
      }, 500);
    }, 3000);
  }, [props.setOpen]);

  return (
    <Transition nodeRef={alertRef} in={show} timeout={100}>
      {(state) => (
        <div
          ref={alertRef}
          className={`w-2/4 fixed bottom-20 right-1 ${state === 'entered' ? 'transition-all ease-out duration-500 transform translate-y-10 opacity-100' : 'transition-all ease-in duration-500 transform -translate-y-0 opacity-0'
            }`}
        >
          <Alert>
            <CircleAlert className="h-4 w-4" />
            <AlertTitle>{props.title}</AlertTitle>
            <AlertDescription>{props.mess}</AlertDescription>
          </Alert>
        </div>
      )}
    </Transition>
  );
}
