import { keyframes } from "@emotion/react";

export const fadeInAndUpAnimation = keyframes`
from {
    opacity: 0;
    transform: translateY(50px);
}
to {
    opacity: 1;
    transform: translateY(0);
}`;

export const fadeOut = keyframes`
  from {
    opacity: 1;
  }
  to {
    opacity: 0;
  }
`;

export const fadeIn = keyframes`
from {
    opacity: 0;
}
to {
    opacity: 1;
}`;


