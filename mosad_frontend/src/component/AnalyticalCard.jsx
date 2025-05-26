import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { styled, useTheme } from '@mui/material/styles';
import LinearProgress from '@mui/material/LinearProgress';
import TrendingUpIcon from '@mui/icons-material/TrendingUp';
import CategoryIcon from '@mui/icons-material/Category';
import Inventory2Icon from '@mui/icons-material/Inventory2';
import ShoppingCartCheckoutIcon from '@mui/icons-material/ShoppingCartCheckout';
import { teal } from '@mui/material/colors'; // Import the teal color palette


function AnalyticalCard({stats}) {
  const theme = useTheme();

  console.log(stats);


  
const customLimits = {
  categories:50,
  brands:50,
  items:1000,
  daily_goal: 50
}

const analyticalData = [
  {
    id: 1,
    title: 'Total Item Categories',
    value: stats.totalCategories,
    progress: (5 / customLimits.categories)*100,
    icon: CategoryIcon,
  },
  {
    id: 2,
    title: 'Total Brands We Have',
    value: stats.totalBrands,
    progress: (25 /customLimits.brands)*100,
    icon: TrendingUpIcon,
  },
  {
    id: 3,
    title: 'Total Items in Stock',
    value: stats.totalItems,
    progress: (342/customLimits.items)*100,
    icon: Inventory2Icon,
  },
  {
    id: 4,
    title: 'Total Bills Issued Today',
    value: stats.totalBillsToday,
    progress: (15/customLimits.daily_goal)*100,
    icon: ShoppingCartCheckoutIcon,
  },
];

const ModernCard = styled(Card)(({ theme }) => ({
  background: teal[500], // Use teal[500] for the main background color
  color: theme.palette.common.white,
  borderRadius: theme.spacing(1.5),
  boxShadow: `0 4px 8px rgba(0, 0, 0, 0.1)`,
  transition: 'transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out',
  display: 'flex',
  flexDirection: 'column',
  height: 'auto',
  '&:hover': {
    transform: 'translateY(-3px)',
    boxShadow: `0 6px 12px rgba(0, 0, 0, 0.15)`,
  },
}));

const CardContentWrapper = styled(CardContent)(({ theme }) => ({
  flexGrow: 1,
  display: 'flex',
  flexDirection: 'column',
  justifyContent: 'center',
  alignItems: 'center',
  padding: theme.spacing(1),
  textAlign: 'center',
}));

const IconWrapper = styled(Box)(({ theme }) => ({
  fontSize: '1.5rem',
  marginBottom: theme.spacing(0.75),
  color: theme.palette.primary.contrastText,
}));

const TitleTypography = styled(Typography)(({ theme }) => ({
  fontSize: '0.8rem',
  fontWeight: 500,
  color: theme.palette.common.white,
  opacity: 0.8,
  marginBottom: theme.spacing(0.0),
}));

const ValueTypography = styled(Typography)(({ theme }) => ({
  fontSize: '1.3rem',
  fontWeight: 500,
  color: theme.palette.common.white,
  marginBottom: theme.spacing(0.5),
}));

const ProgressContainer = styled(Box)(({ theme }) => ({
  width: '100%',
  borderRadius: theme.spacing(0.5),
  overflow: 'hidden',
  marginTop: theme.spacing(0.5),
}));

const ModernLinearProgress = styled(LinearProgress)(({ theme }) => ({
  backgroundColor: 'rgba(255, 255, 255, 0.15)',
  borderRadius: theme.spacing(0.5),
  height: 2,
  '& .MuiLinearProgress-bar': {
    backgroundColor: theme.palette.primary.contrastText,
    borderRadius: theme.spacing(0.5),
  },
}));







  return (
    <Box
      sx={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        padding: theme.spacing(2),
        gap: theme.spacing(4)
      }}
    >
        {analyticalData.map((data) => (
          <ModernCard key={data.id}>
            <CardContentWrapper>
              {data.icon && (
                <IconWrapper>
                  <data.icon />
                </IconWrapper>
              )}
              <TitleTypography variant="overline" display="block">
                {data.title}
              </TitleTypography>
              <ValueTypography variant="subtitle2">{data.value}</ValueTypography>
              {data.progress !== undefined && (
                <ProgressContainer>
                  <ModernLinearProgress variant="determinate" value={data.progress} />
                </ProgressContainer>
              )}
            </CardContentWrapper>
          </ModernCard>
        ))}
      </Box>
  );
}

export default AnalyticalCard;